package com.example.myfirstapp;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(modules = {DemoDaggerModuleTest.class})
public interface DemoDaggerComponent {
    //添加@Module 注解

    //添加@Component
    //添加module
    void intect(MainActivity appleActivity);

}
