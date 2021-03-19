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
  allPersons: Person[] = [];

  dataSource = new MatTableDataSource<Team>();
  displayedColumns: string[] = ['identifiant', 'name', 'display', 'update'];

  constructor(private teamService: TeamService, private dialog: MatDialog,
              private readonly onlineOfflineService: OnlineOfflineService,
              private personService: PersonService) { }

  ngOnInit(): void {
    this.refresh();

    this.personService.getAllPersons().subscribe(persons => {
      this.allPersons = persons.map(item => {
        item.checked = false;
        return item;
      });
    })
  }

  async refresh() {
    if(!this.onlineOfflineService.isOnline) {
      this.dataSource.data = await localforage.getItem('teams');
      this.allPersons = await localforage.getItem('persons');
    } else {
      this.teamService.getAllTeams().subscribe(data => {
        this.dataSource.data = data;
      });
      this.personService.getAllPersons().subscribe(persons => {
        this.allPersons = persons.map(item => {
          item.checked = false;
          return item;
        });
      });
    }
  }

  display(element: Team) {
    this.toDisplay = this.toDisplay.length === 0 ? element.list : [];
  }

  delete(element: Team) {
    if (this.onlineOfflineService.isOnline) {
      this.teamService.delete(element.uuid);
    } else {
      this.teamService.deleteFromLocalForage(element.uuid);
    }
    setTimeout(() => this.refresh(), 100);
  }

  update(element: Team) {
    this.team = element;
    this.action = 'update';
    this.allPersons = this.allPersons.map(item => {
      item.checked = !!element.list.find(person => person.uuid === item.uuid);
      return item;
    });
    this.openDialog();
  }

  create() {
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
        if(data) {
          if(this.action === 'creation') {
            if (this.onlineOfflineService.isOnline) {
              this.teamService.create(data).subscribe(data => console.log(data));
            } else {
              this.dataSource.data.push(data);
              this.teamService.addToLocalForage(data);
            }
          } else {
            if (this.onlineOfflineService.isOnline) {
              this.teamService.update(data).subscribe();
            } else {
              this.dataSource.data.push(data);
              this.teamService.updateFromLocalForage(data);
            }
          }
        }
        setTimeout(() => this.refresh(), 100);
      }
    );
  }
}
