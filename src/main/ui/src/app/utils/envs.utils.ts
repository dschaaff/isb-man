export class EnvsUtils {

  public static toArray(envs) {
    let retVal = [];
    for (let index in envs) {
      let source = envs[index];
      for (let name in source) {
        let vv = source[name];
        let env = { source: index, name: name, value: vv};
        retVal.push(env);
      }
    }
    return retVal;
  }

}
