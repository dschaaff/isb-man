import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppComponent } from './app.component';

// Import containers
import {
  FullLayoutComponent,
  SimpleLayoutComponent
} from './containers';

const APP_CONTAINERS = [
  FullLayoutComponent,
  SimpleLayoutComponent
]

// Import components
import {
  AppBreadcrumbsComponent,
  AppFooterComponent,
  AppHeaderComponent,
  AppSidebarComponent,
  AppSidebarMinimizerComponent,
  APP_SIDEBAR_NAV
} from './components';

const APP_COMPONENTS = [
  AppBreadcrumbsComponent,
  AppFooterComponent,
  AppHeaderComponent,
  AppSidebarComponent,
  AppSidebarMinimizerComponent,
  APP_SIDEBAR_NAV
]

// Import directives
import {
  NAV_DROPDOWN_DIRECTIVES,
  ReplaceDirective,
  SIDEBAR_TOGGLE_DIRECTIVES
} from './directives';

const APP_DIRECTIVES = [
  NAV_DROPDOWN_DIRECTIVES,
  ReplaceDirective,
  SIDEBAR_TOGGLE_DIRECTIVES
]

// Import routing module
import { AppRoutingModule } from './app.routing';

// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts/ng2-charts';
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

@NgModule({
  imports: [
    BrowserModule,BrowserAnimationsModule,FormsModule,
    AppRoutingModule,
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    ClipboardModule, ToastModule.forRoot(),
    HttpModule
  ],
  declarations: [
    AppComponent,
    ServersPage,EnvModal,TraceModal,TraceDetailsModal,
    MetricsPage,MetricsTab, TimersTable, CountersTable, GaugesTable, MetersTable,
    DTable,
    ...APP_CONTAINERS,
    ...APP_COMPONENTS,
    ...APP_DIRECTIVES
  ],
  entryComponents: [EnvModal,TraceModal, TraceDetailsModal],
  providers: [{
    provide: LocationStrategy,
    useClass: HashLocationStrategy
  }, StateService, ServerService],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
