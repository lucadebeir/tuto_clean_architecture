import { Component, OnInit } from '@angular/core';
import {Observable, of} from "rxjs";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogComponent} from "../component/dialog/dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {Person} from "../../../models/person.model";
import {PersonService} from "../../../services/person/person.service";
import {OnlineOfflineService} from "../../../services/online-offline/online-offline.service";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.scss']
})
export class PersonListComponent implements OnInit {

  allPersons$: Observable<Person[]> | undefined;
  person!: Person;
  action: string = '';

  dataSource = new MatTableDataSource<Person>();
  displayedColumns: string[] = ['identifiant', 'firstname', 'lastname', 'age', 'update'];

  constructor(private personService: PersonService, private dialog: MatDialog,
              private readonly onlineOfflineService: OnlineOfflineService) { }

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.personService.getAllPersons().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  update(p: Person) {
    this.action = 'update';
    if (p.uuid != null) {
      this.personService.getPersonByUuid(p.uuid).subscribe((value => {
        this.person = {
          firstName: value.firstName,
          lastName: value.lastName,
          age: value.age
        };
        this.openDialog()
      }))
    }
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
        firstName: this.action === 'update' ? this.person.firstName : '',
        lastName: this.action === 'update' ? this.person.lastName : '',
        age: this.action === 'update' ? this.person.age : '',
        title: this.action === 'update' ? "Modifier une personne" : "Créer une personne"
      };

    const dialogRef = this.dialog.open(DialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      (data: Person) => {
        console.log("Dialog output:", data)
        if(this.action === 'creation') {
          data.age = parseInt(String(data.age))
          console.log(typeof data.age)
          if(this.onlineOfflineService.isOnline) {
            this.personService.create(data).subscribe(data => {
              console.log(data)
              this.refresh()
            });
          } else {
            this.dataSource.data.push(data);
            this.personService.addToIndexedDb(data).then(r => console.log(r));
          }
        } else {

        }
      }
    );
  }
}
