#!/bin/bash

# startup.sh - SportPath Application Startup Script for Linux

export no_proxy="localhost,127.0.0.1,0.0.0.0"
export NO_PROXY="localhost,127.0.0.1,0.0.0.0"

echo -e "\033[36m=== SportPath Application Startup ===\033[0m"
echo -e "\033[32mStarting infrastructure services...\033[0m"

# Запуск базовой инфраструктуры с sudo
sudo docker-compose up -d postgres redis zookeeper kafka vault

echo -e "\033[33mWaiting for Vault to be ready...\033[0m"
vault_ready=false
vault_retries=0
max_vault_retries=30

while [ $vault_retries -lt $max_vault_retries ]; do
    sleep 3
    if curl -s http://localhost:8200/v1/sys/health > /dev/null 2>&1; then
        vault_ready=true
        echo -e "\033[32m✓ Vault is ready!\033[0m"
        break
    else
        vault_retries=$((vault_retries + 1))
        if [ $vault_retries -ge $max_vault_retries ]; then
            echo -e "\033[31m✗ Vault failed to start within timeout\033[0m"
            exit 1
        fi
        echo -e "\033[90m  Vault not ready yet... (attempt $vault_retries/$max_vault_retries)\033[0m"
    fi
done

echo -e "\033[32mLoading secrets into Vault...\033[0m"

# Настройки Vault
vault_token="root"
vault_addr="http://localhost:8200"

# Функция для загрузки JSON файлов в Vault через curl
import_secret_to_vault() {
    local file_path="$1"
    local secret_path="$2"

    if [ ! -f "$file_path" ]; then
        echo -e "\033[33mWarning: File $file_path not found\033[0m"
        return 1
    fi

    # Читаем JSON файл
    local json_content=$(cat "$file_path")

    # Создаем payload в формате, который ожидает Vault
    local payload=$(jq -n --argjson data "$json_content" '{"data": $data}')

    if curl -s -X POST \
        -H "X-Vault-Token: $vault_token" \
        -H "Content-Type: application/json" \
        -d "$payload" \
        "$vault_addr/v1/secret/data/$secret_path" > /dev/null; then
        echo -e "\033[32m  ✓ Loaded: $secret_path\033[0m"
        return 0
    else
        echo -e "\033[31m  ✗ Failed to load $secret_path\033[0m"
        return 1
    fi
}

# Загрузка JSON файлов
secret_files=(
    "./secrets/auth-service.json:auth-service"
    "./secrets/courts-service.json:courts-service"
    "./secrets/entry-service.json:entry-service"
    "./secrets/notifications-service.json:notifications-service"
    "./secrets/gateway-server.json:gateway-server"
    "./secrets/eureka-server.json:eureka-server"
)

loaded_count=0
total_count=${#secret_files[@]}

for secret_pair in "${secret_files[@]}"; do
    file_path="${secret_pair%:*}"
    vault_path="${secret_pair#*:}"

    echo -e "\033[36mLoading secret: $vault_path\033[0m"

    if import_secret_to_vault "$file_path" "$vault_path"; then
        loaded_count=$((loaded_count + 1))
    fi
done

echo -e "\033[32mSecrets loaded: $loaded_count/$total_count\033[0m"

echo -e "\033[32mStarting Config Server...\033[0m"
sudo docker-compose up -d config-server

echo -e "\033[33mWaiting for Config Server...\033[0m"
config_ready=false
config_retries=0
max_config_retries=20

while [ $config_retries -lt $max_config_retries ]; do
    sleep 5
    response=$(curl -s http://localhost:8071/actuator/health 2>/dev/null)
    if [ $? -eq 0 ] && [ -n "$response" ]; then
        status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
        if [ "$status" = "UP" ]; then
            config_ready=true
            echo -e "\033[32m✓ Config Server is ready!\033[0m"
            break
        fi
    fi

    config_retries=$((config_retries + 1))
    if [ $config_retries -ge $max_config_retries ]; then
        echo -e "\033[31m✗ Config Server failed to start within timeout\033[0m"
        exit 1
    fi
    echo -e "\033[90m  Config Server not ready yet... (attempt $config_retries/$max_config_retries)\033[0m"
done

echo -e "\033[32mStarting Eureka Server...\033[0m"
sudo docker-compose up -d eureka-server

echo -e "\033[33mWaiting for Eureka Server...\033[0m"
eureka_ready=false
eureka_retries=0
max_eureka_retries=20

while [ $eureka_retries -lt $max_eureka_retries ]; do
    sleep 5
    response=$(curl -s http://localhost:8761/actuator/health 2>/dev/null)
    if [ $? -eq 0 ] && [ -n "$response" ]; then
        status=$(echo "$response" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
        if [ "$status" = "UP" ]; then
            eureka_ready=true
            echo -e "\033[32m✓ Eureka Server is ready!\033[0m"
            break
        fi
    fi

    eureka_retries=$((eureka_retries + 1))
    if [ $eureka_retries -ge $max_eureka_retries ]; then
        echo -e "\033[31m✗ Eureka Server failed to start within timeout\033[0m"
        exit 1
    fi
    echo -e "\033[90m  Eureka Server not ready yet... (attempt $eureka_retries/$max_eureka_retries)\033[0m"
done

echo -e "\033[32mStarting all microservices...\033[0m"
# БЕЗ cloudpub - только основные сервисы
sudo docker-compose up -d gateway-server auth-service courts-service entry-service notifications-service

# Даем время всем сервисам запуститься
echo -e "\033[33mWaiting for all services to initialize...\033[0m"
sleep 15

echo -e "\n\033[36m=== Startup Complete! ===\033[0m"
echo -e "\033[32mAll services are starting up!\033[0m"
echo -e "\n\033[33mImportant URLs:\033[0m"
echo -e "  Eureka Dashboard: http://localhost:8761"
echo -e "  API Gateway:      http://localhost:8072"
echo -e "  Vault UI:         http://localhost:8200"
echo -e "  Token:            root"

# Показать статус всех сервисов
echo -e "\n\033[36mCurrent container status:\033[0m"
sudo docker-compose ps

echo -e "\n\033[32mYou can monitor services in Eureka: http://localhost:8761\033[0m"