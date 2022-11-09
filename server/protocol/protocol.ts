enum OptType {
    draw = 0,
    delete = 1,
    change = 2,
}

// 画图类型
enum DrawType {
//     画笔: 0;
//     字: 1;
//     xx图形: ...;
// ...
}

// 暂时这么写
type dataType = {
    beginPoint: [number, number];
    endPoint: [number, number];
} | {
    text: string;
}

interface IDrawType {
    type: DrawType; // 画图类型
    data: dataType;
    lineWidth: number; // 线条粗细 | 字体大小
    lineColor: number; // 线条字体颜色
    userId: string; // 谁画的
}

interface IDrawAPI {
    version: number; // 版本
    drawId: number; // 图像id
    opt: OptType; //操作类型
    pictureData?: IDrawType; // 删除的时候不需要
    sheetId: number;
}

interface ISheetApi {
    version: number;
    newSheetId: number;
}

interface IReadOnlyApi {
    version: number;
    isReadOnly: number; // 0否，1是
}

interface IConnectApi {
    version: number;
    boardList: {
        sheetId: number;
        drawData: IDrawType[];
    }
}

// 撤销的时候 list.pop();
// 重做 list.push();