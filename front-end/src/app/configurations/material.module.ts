import {NgModule} from "@angular/core";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatTabsModule} from "@angular/material/tabs";

const modules = [
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule,
  MatTabsModule
];

@NgModule({
  imports: [modules],
  exports: [modules],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
  ]
})
export class MaterialModule {}
