package com.clean.architecture.tuto.reposql.config;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;

@RunWith(JUnit4.class)
public class ConfigUT {

    @Test
    public void should_display_same_address_when_singleton_is_used() throws SQLException {
        String address = Config.SingletonSQL.getInstance().getConnection().toString();
        Assertions.assertThat(address).isNotNull();
        Assertions.assertThat(address).isNotEmpty();

        for(int i = 0; i < 3000; ++i) {
            String otherAddress = Config.SingletonSQL.getInstance().getConnection().toString();
            Assertions.assertThat(address).isEqualTo(otherAddress);
        }
    }

}
