package com.example.persistence;

import com.example.model.Type;
import org.junit.jupiter.api.Test;

public class HsqldbRepositoryTest {

    HsqldbRepository dbRepo = new HsqldbRepository();

    // there should be at least findAll() method in the repository class, to be able to test

    @Test
    public void insert() {
        dbRepo.insert(new EventEntity("id", 128L, false, Type.APPLICATION_LOG, "host"));
    }
}