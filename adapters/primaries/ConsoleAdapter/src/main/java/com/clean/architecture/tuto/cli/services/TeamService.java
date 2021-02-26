    package com.clean.architecture.tuto.cli.services;

    import com.clean.architecture.tuto.cli.config.Config;
    import com.clean.architecture.tuto.cli.utils.GuiUtils;
    import com.clean.architecture.tuto.core.exceptions.BusinessException;
    import com.clean.architecture.tuto.core.exceptions.TechnicalException;
    import com.clean.architecture.tuto.core.models.Person;
    import com.clean.architecture.tuto.core.models.Team;

    import java.util.*;

    import static com.clean.architecture.tuto.cli.constantes.ConstApp.ERROR_TECHNICAL;

    public class TeamService {

        public void create(Scanner in) throws InterruptedException {
            try {
                GuiUtils.displayTitle("CREATION D'UNE EQUIPE");
                System.out.println("Nom : ");
                String name = in.nextLine();
                Team newTeam = Team.builder().name(name).build();

                System.out.println("Vous allez rentrer les identifiants des personnes rejoignant cette équipe.");
                System.out.println("Séparer les identifiants par une virgule.");
                System.out.println("Par exemple : ");
                System.out.println("1,2,3,4,5,6");
                System.out.println(" ");
                System.out.println("Entrer les identifiants des personnes rejoignant cette équipe : ");

                List<Person> personList = new ArrayList<>();

                String id = in.nextLine();
                if(!id.isEmpty()) {
                    List<String> list = Arrays.asList(id.split(","));

                    list.forEach(element -> {
                        try {
                            Optional<Person> optionalPerson = Config.findByIdPersonUseCase().execute(element);
                            optionalPerson.ifPresent(person -> personList.add(person));
                        } catch (BusinessException e) {
                            System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
                        } catch (TechnicalException e) {
                            System.err.println(ERROR_TECHNICAL);
                        }
                    });
                }

                newTeam.setList(personList);
                newTeam = Config.getCreateTeamUseCase().execute(newTeam);

                System.out.println(newTeam.getName() + " a l'identifiant " + newTeam.getId());
            } catch (BusinessException e) {
                System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
                Thread.sleep(1500);
            } catch (Exception e) {
                System.err.println(ERROR_TECHNICAL);
            }
        }

        public void findAll() {
            GuiUtils.displayTitle("AFFICHAGE DE TOUTES LES EQUIPES");
            try {
                List<Team> list = Config.getAllTeamUseCase().execute();
                System.out.println("Identifiant | Nom de l'équipe");
                list.forEach(t -> {
                    System.out.println(t.getId() + " | " + t.getName());
                    System.out.println("Identifiant | Prenom | Nom | Age");
                    t.getList().forEach(p -> System.out.println(p.getId() + " | " + p.getFirstName() + " | " + p.getLastName() + " | " + p.getAge()));
                    System.out.println("-----------------------------------------------");
                });
            } catch (TechnicalException e) {
                System.err.println(ERROR_TECHNICAL);
            }

        }

        public void findById(Scanner in) throws InterruptedException {
            try {
                GuiUtils.displayTitle("AFFICHAGE DES DETAILS D'UNE EQUIPE");
                System.out.println("Id : ");
                String id = in.nextLine();

                Optional<Team> optionalTeam = Config.findByIdTeamUseCase().execute(id);

                optionalTeam.ifPresent(team -> {
                    System.out.println("Nom de l'equipe : " + team.getName());
                    System.out.println("Membres de l'equipe :");
                    team.getList().forEach(p -> System.out.println(p.getFirstName() + " " + p.getLastName() + " a " + p.getAge() + "ans"));
                });

            } catch (BusinessException e) {
                System.err.println(String.join(System.lineSeparator(), e.getErrorsList()));
                Thread.sleep(1500);
            }  catch (Exception e) {
                System.err.println(ERROR_TECHNICAL);
            }
        }

    }
