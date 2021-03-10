import { Component, OnInit } from '@angular/core';
import {Person} from "../../../core/domain/person.model";
import {GetAllPersonsUsecase} from "../../../core/usecases/get-all-persons.usecase";
import {GetPersonByIdUsecase} from "../../../core/usecases/get-person-by-id.usecase";
import {Observable, of} from "rxjs";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CreatePersonUsecase} from "../../../core/usecases/create-person.usecase";
import {DialogComponent} from "../component/dialog/dialog.component";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.scss']
})
export class PersonListComponent implements OnInit {

  allPersons$?: Observable<Person[]>;
  person!: Person;
  action: string = '';

  dataSource = new MatTableDataSource<Person>();
  displayedColumns: string[] = ['identifiant', 'firstname', 'lastname', 'age', 'update'];

  constructor(private getAllPersons: GetAllPersonsUsecase, private getPersonById: GetPersonByIdUsecase,
              private dialog: MatDialog, private createPerson: CreatePersonUsecase) { }

  ngOnInit(): void {
    this.refresh();
  }

  refresh() {
    this.allPersons$ = this.getAllPersons.execute();

    this.getAllPersons.execute().subscribe(data => {
      this.dataSource.data = data;
    });
  }

  update(p: Person) {
    this.action = 'update';
    if (p.id != null) {
      this.getPersonById.execute(p.id).subscribe((value => {
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
      data => {
        console.log("Dialog output:", data)
        if(this.action === 'creation') {
          this.createPerson.execute(data).subscribe(data => {
            console.log(data)
            this.refresh()
          });
        } else {

        }
      }
    );
  }
}
