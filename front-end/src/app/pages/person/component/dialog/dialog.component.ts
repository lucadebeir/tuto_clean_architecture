import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Person} from "../../../../models/person.model";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {

  form!: FormGroup;
  person: Person = {
    uuid: "",
    firstName: "",
    lastName: "",
    age: 0,
  };
  title: string = "";

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<DialogComponent>,
              @Inject(MAT_DIALOG_DATA) {uuid, firstName, lastName, age, title}: any) {
    this.person.uuid = uuid;
    this.person.firstName = firstName;
    this.person.lastName = lastName;
    this.person.age = age;
    this.title = title;

    this.form = this.fb.group({
      firstName: [this.person.firstName, Validators.required],
      lastName: [this.person.lastName, Validators.required],
      age: [this.person.age, Validators.required]
    })
  }

  ngOnInit(): void {
  }

  init(): void {

  }

  save(): void {
    this.person.firstName = this.form.value.firstName;
    this.person.lastName = this.form.value.lastName;
    this.person.age = parseInt(String(this.form.value.age));
    this.dialogRef.close(this.person);
  }

  close(): void {
    this.dialogRef.close();
  }

}
