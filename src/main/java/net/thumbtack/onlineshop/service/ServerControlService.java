package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.database.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerControlService {

    private List<Dao> dao;

    @Autowired
    public ServerControlService(List<Dao> dao) {
        this.dao = dao;
    }

    /**
     * Удаляет все записи из базы данных
     */
    public void clear() {
        dao.forEach(Dao::clear);
    }
}
