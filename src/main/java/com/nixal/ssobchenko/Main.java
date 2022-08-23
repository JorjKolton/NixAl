package com.nixal.ssobchenko;

import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.util.ApplicationContextImpl;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        final ApplicationContextImpl apl = new ApplicationContextImpl();

        BusService bs = apl.getObject(BusService.class);
        Field instance = bs.getClass().getDeclaredField("instance");
        instance.setAccessible(true);
        System.out.println(instance.get(bs).hashCode());
        System.out.println(bs.hashCode());

        BusService bs2 = apl.getObject(BusService.class);
        Field instance1 = bs2.getClass().getDeclaredField("instance");
        instance1.setAccessible(true);
        System.out.println(instance1.get(bs).hashCode());
        System.out.println(bs2.hashCode());

    }
}