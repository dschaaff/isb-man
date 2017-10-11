import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import {ServersPage} from "./pages/servers/servers.page";
import {MetricsPage} from "./pages/metrics/metrics.page";
import {routing} from "./app.routes";
import {RouterModule} from "@angular/router";
import {Sidenav} from "./layout/sidenav";
import {Topbar} from "./layout/topbar";
import {StateService} from "./services/state.service";
import {ServerService} from "./services/server.service";
import {HttpModule} from '@angular/http';
import {MetricsTab} from "./pages/metrics/metrics.tab";
import {TimersTable} from "./pages/metrics/timers.table";
import {CountersTable} from "./pages/metrics/counters.table";
import {MetersTable} from "./pages/metrics/meters.table";
import {GaugesTable} from "./pages/metrics/gauges.table";
import { ClipboardModule } from 'ngx-clipboard';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,Topbar,Sidenav,
    ServersPage, MetricsPage, MetricsTab, TimersTable, CountersTable, GaugesTable, MetersTable
  ],
  imports: [
    routing, RouterModule,
    NgbModule.forRoot(),BrowserModule,BrowserAnimationsModule, HttpModule,
    ClipboardModule, ToastModule.forRoot()
  ],
  providers: [StateService,ServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
