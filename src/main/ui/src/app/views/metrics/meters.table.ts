import {Component, Input} from "@angular/core";

@Component({
  selector: 'meters-table',
  templateUrl: './meters.table.html'
})
export class MetersTable {
  @Input() meters: any[] = [];
}
