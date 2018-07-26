package main.Controllers;

import main.Services.ICrud;
import main.Services.Impl.Crud;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ICrud crud = new Crud();
        crud.delete("DELETE FROM ReadyToFight");
        crud.delete("DELETE FROM Challenge");
        crud.delete("DELETE FROM Fight");
        crud.delete("DELETE FROM FightLog");
        System.out.println("================================================================");
    }
}
