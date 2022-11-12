package com.fjeldsted.DB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.tinylog.Logger;

public class Database {
    private SessionFactory sessionFactory;

    public Database() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from
                                                // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble
            // building the SessionFactory
            // so destroy it manually.
            Logger.error(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public SessionFactory getSession() {
        return this.sessionFactory;
    }
}
