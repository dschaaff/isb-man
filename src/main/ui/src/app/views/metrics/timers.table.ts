import {Component, Input} from "@angular/core";
import {DColumn} from "../../components/dtable/dtable";

@Component({
  selector: 'timers-table',
  templateUrl: './timers.table.html'
})
export class TimersTable {
  @Input() timers: any[] = [];
}
