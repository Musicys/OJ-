

export const initialData = (str: string) => {
    const isoDateString = str;

    // 将 ISO 时间字符串转换为 Date 对象
    const date = new Date(isoDateString);

    // 获取年、月、日
    const year = date.getUTCFullYear(); // 使用 UTC 方法避免时区问题
    const month = String(date.getUTCMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需加 1
    const day = String(date.getUTCDate()).padStart(2, '0');

    // 拼接成 YYYY-MM-DD 格式
    return `${year}-${month}-${day}`;
}