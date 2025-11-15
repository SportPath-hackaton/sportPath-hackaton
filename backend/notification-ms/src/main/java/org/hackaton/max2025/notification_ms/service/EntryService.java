package org.hackaton.max2025.notification_ms.service;

import org.hackaton.max2025.notification_ms.model.EntryChangeModel;

public interface EntryService {

    void saveEntry(EntryChangeModel entryChangeModel);
    void deleteEntry(EntryChangeModel entryChangeModel);
}
