import {Component, Input, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Team} from "../../../../models/team.model";
import {Person} from "../../../../models/person.model";

@Component({
  selector: 'app-team-details',
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.scss']
})
export class TeamDetailsComponent implements OnInit {

  @Input('persons')
  persons: Person[] = [];

  dataSource = new MatTableDataSource<Person>();
  displayedColumns: string[] = ['identifiant', 'firstname', 'lastname', 'age'];

  constructor() { }

  ngOnInit(): void {
    console.log(this.persons)
    this.dataSource.data = this.persons;
  }

}
