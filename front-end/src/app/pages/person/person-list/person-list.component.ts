import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogComponent} from "../component/dialog/dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {Person} from "../../../models/person.model";
import {PersonService} from "../../../services/person/person.service";
import {OnlineOfflineService} from "../../../services/online-offline/online-offline.service";
import * as localforage from "localforage";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.scss']
})
export class PersonListComponent implements OnInit {

  person!: Person;
  action: string = '';

  dataSource = new MatTableDataSource<Person>();
  displayedColumns: string[] = ['identifiant', 'firstname', 'lastname', 'age', 'update'];

  constructor(private personService: PersonService, private dialog: MatDialog,
              private readonly onlineOfflineService: OnlineOfflineService) { }

  ngOnInit(): void {
    this.refresh();
  }

  async refresh() {
    if(!this.onlineOfflineService.isOnline) {
      this.dataSource.data = await localforage.getItem('persons');
    } else {
      this.personService.getAllPersons().subscribe(data => {
        this.dataSource.data = data;
      });
    }
    console.log(this.dataSource.data)
  }

  async update(p: Person) {
    console.log(p)
    this.action = 'update';
    if (p.uuid != null) {
      if (this.onlineOfflineService.isOnline) {
        this.personService.getPersonByUuid(p.uuid).subscribe((value => {
          this.person = value;
        }));
      } else {
        this.person = await this.personService.getPersonByUuidFromLocalForage(p.uuid);
      }
      setTimeout(() => this.openDialog(), 100);
    }
  }

  delete(p: Person) {
    if (this.onlineOfflineService.isOnline) {
      this.personService.delete(p.uuid);
    } else {
      this.personService.deleteFromLocalForage(p.uuid);
    }
    setTimeout(() => this.refresh(), 100);
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
      uuid: this.action === 'update' ? this.person.uuid : '',
      firstName: this.action === 'update' ? this.person.firstName : '',
      lastName: this.action === 'update' ? this.person.lastName : '',
      age: this.action === 'update' ? this.person.age : '',
      title: this.action === 'update' ? "Modifier une personne" : "Créer une personne"
    };

    const dialogRef = this.dialog.open(DialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      (data: Person) => {
        console.log("Dialog output:", data);
        if(this.action === 'creation') {
          if (this.onlineOfflineService.isOnline) {
            this.personService.create(data).subscribe();
          } else {
            this.dataSource.data.push(data);
            this.personService.addToLocalForage(data);
          }
        } else {
          if (this.onlineOfflineService.isOnline) {
            this.personService.update(data).subscribe();
          } else {
            this.dataSource.data.push(data);
            this.personService.updateFromLocalForage(data);
          }
        }
        setTimeout(() => this.refresh(), 100);
      }
    );
  }
}
