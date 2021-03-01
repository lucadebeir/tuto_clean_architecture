package com.clean.architecture.tuto.reposql.repositories;

import com.clean.architecture.tuto.reposql.config.Config;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;

@Getter
@Setter
public abstract class AbstractRepositorySQL {

    protected Connection connection;

    public AbstractRepositorySQL() {
        try {
            connection = Config.SingletonSQL.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
