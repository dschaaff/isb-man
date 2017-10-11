export class CollectionUtils {

  public static toSet(arr: any[], field: string) {
    let o: any = {};
    let l = arr.length;
    let r: any[] = [];
    for (let i = 0; i < l; i += 1) {
      o[arr[i][field]] = '';
    }
    for (let oi in o) {
      if (o.hasOwnProperty(oi)) {
        r.push(oi);
      }
    }
    return r.sort();
  }

  public static posInArray(value: string, arr: any[]): number {
    for (let index in arr) {
      if (value === arr[index]) {
        return parseInt(index,10);
      }
    }
    return -1;
  }


}
