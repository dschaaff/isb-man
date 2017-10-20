import {Component, Input} from "@angular/core";

@Component({
  selector: 'counters-table',
  templateUrl: './counters.table.html'
})
export class CountersTable {
  @Input() counters: any[] = [];
}
