import { Component } from '@angular/core';
import {StateService} from "../../services/state.service";
import {ServerService} from "../../services/server.service";

@Component({
  templateUrl: './metrics.page.html'
})
export class MetricsPage {
  metrics:any = {timers: [], gauges: [], counters: [], meters: []};
  private subscription: any;

  constructor(public state: StateService, public server: ServerService) {}

  ngOnInit() {
    this.refresh();
    let that = this;
    this.subscription = this.state.change.subscribe(function () {
      that.refresh();
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  refresh() {
    this.server.getMetrics(this.state).subscribe(
      metrics => {
        this.metrics = metrics;
      }
    )
  }

  pre(value) {
    return JSON.stringify(value,null,2);
  }

}
