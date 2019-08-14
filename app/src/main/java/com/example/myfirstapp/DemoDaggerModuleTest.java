package com.example.myfirstapp;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class DemoDaggerModuleTest {
    @Provides
    TestEntity DemoTest() {
        return new TestEntity();
    }
    public static class TestEntity {
        String testContent = "test dagger data";

        public TestEntity() {
        }
    }
}