import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';
import {ServersPage} from "./views/servers/servers.page";
import {StateService} from "./services/state.service";
import {ServerService} from "./services/server.service";
import {MetricsPage} from "./views/metrics/metrics.page";
import {MetersTable} from "./views/metrics/meters.table";
import {GaugesTable} from "./views/metrics/gauges.table";
import {CountersTable} from "./views/metrics/counters.table";
import {TimersTable} from "./views/metrics/timers.table";
import {MetricsTab} from "./views/metrics/metrics.tab";
import {ToastModule} from "ng2-toastr/ng2-toastr";
import { ClipboardModule } from 'ngx-clipboard';
import {HttpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DTable} from "./components/dtable/dtable";
import { FormsModule } from '@angular/forms';
import {EnvModal} from "./views/servers/env.modal";
import {TraceModal} from "./views/servers/trace.modal";
import {TraceDetailsModal} from "./views/servers/trace.details.modal";
import {ThreadsModal} from "./views/servers/threads.modal";
import {ThreadDetailsModal} from "./views/servers/thread.details.modal";
import {routing} from "./app.routing";
import {RouterModule} from "@angular/router";
import {AppHeaderComponent} from "./components/app-header/app-header.component";
import {DatasourcesPage} from "./views/datasources/datasources.page";

@NgModule({
  imports: [
    routing,RouterModule,
    BrowserModule,BrowserAnimationsModule,FormsModule,
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    TabsModule.forRoot(),
    ClipboardModule, ToastModule.forRoot(),
    HttpModule
  ],
  declarations: [
    AppComponent,AppHeaderComponent,
    ServersPage,EnvModal,TraceModal,TraceDetailsModal,ThreadsModal,ThreadDetailsModal,
    MetricsPage,MetricsTab, TimersTable, CountersTable, GaugesTable, MetersTable,
    DatasourcesPage,
    DTable
  ],
  entryComponents: [EnvModal,TraceModal, TraceDetailsModal, ThreadsModal, ThreadDetailsModal],
  providers: [ StateService, ServerService],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
