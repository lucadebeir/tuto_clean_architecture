import {Component, Inject, OnInit} from '@angular/core';
import {Form, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Person} from "../../../../models/person.model";
import {Team} from "../../../../models/team.model";
import {PersonService} from "../../../../services/person/person.service";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {

  form!: FormGroup;
  team: Team = {
    uuid: "",
    name: "",
    list: []
  };
  list: Person[] = [];
  title: string = "";

  constructor(private personService: PersonService,
              private fb: FormBuilder,
              private dialogRef: MatDialogRef<DialogComponent>,
              @Inject(MAT_DIALOG_DATA) {uuid, name, list, title}: any) {
    this.team.uuid = uuid;
    this.team.name = name;
    this.team.list = list;
    this.list = list;
    this.title = title;

    this.form = new FormGroup({
      name: new FormControl(this.team.name, Validators.required),
      list: new FormArray([])
    });

    this.addCheckboxes();
  }

  ngOnInit(): void {
  }

  get listFormArray() {
    return this.form.controls.list as FormArray;
  }

  private addCheckboxes() {
    this.list.forEach((person) => this.listFormArray.push(new FormControl(person.checked)));
  }

  save(): void {
    this.team.name = this.form.value.name;
    this.team.list = this.form.value.list
      .map((checked, i) => checked ? this.team.list[i] : null)
      .filter(v => v !== null);
    this.dialogRef.close(this.team);
  }

  close(): void {
    this.dialogRef.close();
  }

}
