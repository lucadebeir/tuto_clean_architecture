import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {OnlineOfflineService} from "../../../services/online-offline/online-offline.service";
import * as localforage from "localforage";
import {Team} from "../../../models/team.model";
import {TeamService} from "../../../services/team/team.service";
import {Person} from "../../../models/person.model";
import {DialogComponent} from "../components/dialog/dialog.component";
import {map} from "rxjs/operators";
import {PersonService} from "../../../services/person/person.service";

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})
export class TeamListComponent implements OnInit {

  team!: Team;
  action: string = '';
  toDisplay: Person[] = [];
  list: any[] = [];
  allPersons: Person[] = [];

  dataSource = new MatTableDataSource<Team>();
  displayedColumns: string[] = ['identifiant', 'name', 'display', 'update'];

  constructor(private teamService: TeamService, private dialog: MatDialog,
              private readonly onlineOfflineService: OnlineOfflineService,
              private personService: PersonService) { }

  ngOnInit(): void {
    this.refresh();

    this.personService.getAllPersons().subscribe(persons => {
      /*for(let i = 0; i < persons.length; i++) {
        persons[i].checked = false;
      }*/
      persons.forEach(person => {
        person.checked = false;
      });
      setTimeout(() => { this.allPersons = persons }, 1000);
    })
  }

  async refresh() {
    if(!this.onlineOfflineService.isOnline) {
      this.dataSource.data = await localforage.getItem('teams');
    } else {
      this.teamService.getAllTeams().subscribe(data => {
        this.dataSource.data = data;
      });
    }
    console.log(this.dataSource.data)
  }

  display(element: Team) {
    this.toDisplay = this.toDisplay.length === 0 ? element.list : [];
  }

  delete(element: any) {

  }

  update(element: Team) {
    console.log(element)
    this.team = element;
    this.action = 'update';
    this.allPersons.forEach(data => {
      data.checked = (element.list.indexOf(data) !== -1)
    })
    console.log(this.allPersons)
    setTimeout(() => this.openDialog(), 100)
  }

  create() {
    console.log(this.list)
    this.action = 'creation';
    this.openDialog();
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      uuid: this.action === 'update' ? this.team.uuid : '',
      name: this.action === 'update' ? this.team.name : '',
      list: this.allPersons,
      title: this.action === 'update' ? "Modifier une équipe" : "Créer une équipe"
    };

    const dialogRef = this.dialog.open(DialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      (data: Person) => {
        console.log("Dialog output:", data);
        if(this.action === 'creation') {
          if (this.onlineOfflineService.isOnline) {
            this.teamService.create(data).subscribe(data => console.log(data));
          } else {
            this.dataSource.data.push(data);
            //this.teamService.addToLocalForage(data);
          }
        } else {
          if (this.onlineOfflineService.isOnline) {
            //this.personService.update(data).subscribe();
          } else {
            this.dataSource.data.push(data);
            //this.personService.updateFromLocalForage(data);
          }
        }
        //setTimeout(() => this.refresh(), 100);
      }
    );
  }
}
