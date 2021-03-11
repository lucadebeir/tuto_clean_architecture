package com.clean.architecture.tuto.reposql.config.repositories;

import com.clean.architecture.tuto.core.models.Person;
import com.clean.architecture.tuto.core.utils.Utils;
import com.clean.architecture.tuto.reposql.repositories.RepositoryPersonSQL;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@RunWith(JUnit4.class)
public class RepositoryPersonSQL_UT {

    private RepositoryPersonSQL repositoryPerson;

    @Before
    public void setUp() throws SQLException {
        // TODO : Ne fais pas ce que je fais :
        this.repositoryPerson = new RepositoryPersonSQL();
        this.repositoryPerson.createDataSet();
    }

    @After
    public void tearDown() throws SQLException {
        // Ca c'est bon
        this.repositoryPerson.removeDataSet();
    }

    @Test
    public void creer_person_should_return_person_with_id_and_some_informations() throws SQLException, UnsupportedEncodingException {
        Person p = Person.builder().age(18).firstName("Titeuf").lastName("Eponge").build();
        Person person = this.repositoryPerson.create(p);
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getUuid()).isNotNull();
        Assertions.assertThat(person.getUuid()).isNotEmpty();

        Assertions.assertThat(person.getAge()).isEqualTo(18);
        Assertions.assertThat(person.getFirstName()).isEqualTo("Titeuf");
        Assertions.assertThat(person.getLastName()).isEqualTo("Eponge");
    }

    @Test
    public void get_all_should_return_at_least_Romain_and_Vincent() throws SQLException {
        List<Person> allPersons = this.repositoryPerson.getAll();

        Assertions.assertThat(allPersons).isNotNull();
        Assertions.assertThat(allPersons).isNotEmpty();
        Assertions.assertThat(allPersons).hasSizeGreaterThanOrEqualTo(2); // strict minimum syndical

        boolean isRomainPresent = allPersons.stream().anyMatch(p -> p.getLastName().equals("Romain") && p.getFirstName().equals("Chief") && p.getAge().equals(42));
        Assertions.assertThat(isRomainPresent).isTrue();

        boolean isVincentPresent = allPersons.stream().anyMatch(p -> p.getLastName().equals("Vincent") && p.getFirstName().equals("Olivier") && p.getAge().equals(60));
        Assertions.assertThat(isVincentPresent).isTrue();
    }

    @Test
    public void find_by_id_should_return_empty_optional_when_id_is_unknown() throws SQLException {
        Assertions.assertThat(this.repositoryPerson.findByUuid(Utils.getByteArrayFromGuid("99999"))).isNotPresent();
    }

    @Test
    public void find_by_id_should_return_optional_when_id_is_person_created() throws SQLException, UnsupportedEncodingException {
        Person p = Person.builder().age(18).firstName("Titeuf").lastName("Eponge").build();
        Person person = this.repositoryPerson.create(p);
        Assertions.assertThat(this.repositoryPerson.findByUuid(person.getUuid())).isPresent();

        this.repositoryPerson.findByUuid(person.getUuid())
                .ifPresent(pp -> {
                    Assertions.assertThat(pp.getLastName()).isEqualTo("Eponge");
                    Assertions.assertThat(pp.getFirstName()).isEqualTo("Titeuf");
                    Assertions.assertThat(pp.getAge()).isEqualTo(18);
                });
    }

}
