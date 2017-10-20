import { SimpleChanges, Component, Output, Input, EventEmitter} from "@angular/core";
import {CollectionUtils} from "../../utils/collection.utils";

@Component({
  selector: "dtable",
  templateUrl: './dtable.html'
})
export class DTable {
  @Output() change = new EventEmitter();
  @Input() columns: DColumn[] = [];
  @Input() data: any[] = [];
  @Input() template;
  @Input() title;

  filtered: any[] = [];
  sortField: string = null;
  sortOrderAsc: boolean = true;
  filters: any = {};
  filterTimeout: any = null;
  filtersVisible: boolean = false;

  ngOnChanges(changes: SimpleChanges) {
    this.setData(this.data);
  }

  setData(data: any[]) {
    if (typeof data == 'undefined') {
      data = [];
    }
    this.data = data;
    for (let item of data) {
      this.filtered.push(item);
    }
    this.refresh();
  }

  setSortField(field: string) {
    if (field === this.sortField) {
      this.sortOrderAsc = !this.sortOrderAsc;
    } else {
      this.sortField = field;
    }
    this.sortItems();
    return false;
  }

  setSortOrder(asc: boolean) {
    this.sortOrderAsc = asc;
    this.sortItems();
  }

  setFilter(field: string, term: string) {
    this.filters[field] = term;
    this.filterItems();
  }

  setFilters(filters: any) {
    this.filters = filters;
    this.filterItems();
  }

  filterItems() {
    if (this.filterTimeout != null) {
      clearTimeout(this.filterTimeout);
    }

    this.filterTimeout = setTimeout(() => {
      this.doFilter();
    },300);
  }

  refresh() {
    this.doFilter();
    this.sortItems();
  }

  doFilter() {
    this.filtered.length = 0;
    for (let index = 0; index < this.data.length; index++) {
      let value = this.data[index];
      let shouldAdd = true;
      for (let filterField in this.filters) {
        let term = this.filters[filterField];
        let isExclude = term.indexOf('!') === 0;
        let currentVal = "" + CollectionUtils.propertyByString(value, filterField);
        if (typeof currentVal !== 'undefined') {
          if (!isExclude) {
            if (currentVal.toLowerCase().indexOf(term.toLowerCase()) === -1) {
              shouldAdd = false;
            }
          } else {
            if (currentVal.toLowerCase().indexOf(term.substring(1).toLowerCase()) !== -1) {
              shouldAdd = false;
            }
          }
        }
      }
      if (shouldAdd) {
        this.filtered.push(value);
      }
    }
    this.change.emit();
  }

  private sortItems() {
    if (this.sortField == null) {
      return;
    }
    let that = this;
    this.filtered.sort(function(o1:any,o2:any) {
      let v1:any =  CollectionUtils.propertyByString(o1,that.sortField);
      let v2:any = CollectionUtils.propertyByString(o2,that.sortField);
      if (!that.sortOrderAsc) {
        let t = v1;
        v1 = v2;
        v2 = t;
      }
      if (typeof v1 === 'string' && typeof v2 === 'string') {
        return v1.localeCompare(v2);
      }
      if (v1 < v2) {
        return -1;
      }
      if (v2 < v1) {
        return 1;
      }
      return 0;
    });
    this.change.emit();
  }

  hasFilters() {
    for (let col of this.columns) {
      if (col.filterable == true) {
        return true;
      }
    }
    return false;
  }


  toggleFilter() {
    this.filtersVisible = !this.filtersVisible;
  }


}

export interface DColumn {
  title: string;
  filterable: boolean;
  sortable: boolean;
  property: string;
}
