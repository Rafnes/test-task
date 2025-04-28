package me.dineka.comfortsoft.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {
    public List<Integer> readNumbers(String path) {
        List<Integer> numbers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + e.getMessage(), e);
        }
        return numbers;
    }

    public int findNthMinNum(String path, int n) {
        List<Integer> numbers = readNumbers(path);
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Файл пустой");
        }
        if (n > numbers.size()) {
            throw new IllegalArgumentException("Число N превышает количество чисел в файле");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("Число N должно быть больше или равно 1");
        }

        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        return quickSelect(arr, 0, arr.length - 1, n - 1);
    }

    private int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        int pivotIndex = partition(arr, left, right);
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
