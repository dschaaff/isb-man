import {Component, Input} from "@angular/core";

@Component({
  selector: 'timers-table',
  templateUrl: './timers.table.html'
})
export class TimersTable {
  @Input() timers: any[] = [];
}
