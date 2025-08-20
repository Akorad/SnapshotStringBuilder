package org.Akorad;

import java.util.Stack;

public class SnapshotStringBuilder {
    private char[] value; // массив символов
    private int count; // количество символов в массиве
    private Stack<String> history; // стек для хранения снимков

    public SnapshotStringBuilder() {
        this.value = new char[16]; // начальный размер массива
        this.count = 0;
        this.history = new Stack<>();
    }

    /**
     * Добавляет символ в конец строки.
     */
    public void saveSnapshot() {
        history.push(toString());
    }
    /**
     * Увеличение массива при добавлении символов
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > value.length) {
            int newCapacity = Math.max(value.length * 2, minCapacity);
            char[] newValue = new char[newCapacity];
            System.arraycopy(value, 0, newValue, 0, count);
            value = newValue;
        }
    }

    /**
     * Проверяет, находится ли индекс в пределах допустимого диапазона.
     * @param index индекс для проверки
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
        }
    }

    /**
     * Добавляет символ в конец строки.
     * @param obj символ для добавления
     */
    public SnapshotStringBuilder append(Object obj) {
        String str = objectToString(obj);
        saveSnapshot();
        int len = str.length();
        ensureCapacity(count + len);
        str.getChars(0, len, value, count);
        count += len;

        return this;
    }
    /**
     * Удаляет последний символ из строки.
     * @param start начальный индекс для удаления
     * @param end конечный индекс для удаления
     */
    public SnapshotStringBuilder delete(int start, int end) {
        rangeCheck(start);
        saveSnapshot();
        int len = end - start;
        System.arraycopy(value, end, value, start, count - end);
        count -= len;
        return this;
    }

    /**
     * Вставляет строку в указанную позицию.
     * @param offset позиция для вставки
     * @param obj строка для вставки
     */
    public SnapshotStringBuilder insert (int offset, Object obj) {
        rangeCheck(offset);
        String str = objectToString(obj);
        saveSnapshot();
        int len = str.length();
        ensureCapacity(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        str.getChars(0, len, value, offset);
        count += len;
        return this;
    }

    /**
     * Заменяет часть строки на новую строку.
     * @param start начальный индекс для замены
     * @param end конечный индекс для замены
     * @param str новая строка для замены
     */
    public SnapshotStringBuilder replace(int start, int end, String str) {
        if (start < 0 || end > count || start > end) {
            throw new StringIndexOutOfBoundsException("Start index cannot be greater than end index");
        }
        saveSnapshot();
        int len = str.length();
        int oldLen = end - start;
        ensureCapacity(count - oldLen + len);
        System.arraycopy(value, end, value, start + len, count - end);
        str.getChars(0, len, value, start);
        count = count - oldLen + len;
        return this;
    }

    /**
     * Удаляет символ из строки по указанному индексу.
     * @param index индекс символа для удаления
     */
    public SnapshotStringBuilder deleteCharAt(int index) {
        rangeCheck(index);
        saveSnapshot();
        System.arraycopy(value, index + 1, value, index, count - index - 1);
        count--;
        return this;
    }

    /**
     * Находит индекс первого вхождения строки в текущей строке.
     * @param str строка для поиска
     * @return индекс первого вхождения строки, или -1, если не найдено
     */
    public int indexOf(String str, int fromIndex){
        if (fromIndex<0) fromIndex = 0;
        String current = toString();
        return current.indexOf(str, fromIndex);
    }

    /**
     * Находит индекс последнего вхождения строки в текущей строке.
     * @param str строка для поиска
     * @param fromIndex индекс, с которого начинается поиск
     * @return индекс последнего вхождения строки, или -1, если не найдено
     */
    public int lastIndexOf (String str,int fromIndex) {
        if (fromIndex < 0) fromIndex = 0;
        String current = toString();
        if (fromIndex > current.length()) fromIndex = current.length();
        return current.lastIndexOf(str, fromIndex);
    }

    /**
     * Получает символ по указанному индексу.
     * @param index индекс символа
     * @return символ по указанному индексу
     */
    public char charAt(int index) {
        rangeCheck(index);
        return value[index];
    }

    /**
     * Разворачивает строку.
     * @return текущий объект SnapshotStringBuilder после разворота
     */
    public SnapshotStringBuilder reverse() {
        saveSnapshot();
        for (int i = 0, j = count - 1; i < j; i++, j--) {
            char temp = value[i];
            value[i] = value[j];
            value[j] = temp;
        }
        return this;
    }

    /**
     * Устанавливает символ по указанному индексу.
     * @param index индекс, по которому устанавливается символ
     * @param ch символ, который нужно установить
     */
    public void setCharAt(int index, char ch) {
        rangeCheck(index);
        saveSnapshot();
        value[index] = ch;
    }

    /**
     * Получает длину текущей строки.
     * @return длина текущей строки
     */
    public int length() {
        return count;
    }

    /**
     * Отменяет последнее изменение, возвращая строку к предыдущему состоянию.
     */
    public void undo() {
        if (!history.isEmpty()) {
            String prev = history.pop();
            value = new char[Math.max(16, prev.length())];
            prev.getChars(0, prev.length(), value, 0);
            count = prev.length();
        } else {
            throw new IllegalStateException("No snapshots to undo");
        }
    }


    @Override
    public String toString() {
        return new String(value, 0, count);
    }

    private String objectToString(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
}
