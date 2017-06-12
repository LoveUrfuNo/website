/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service.implementation;

import org.springframework.stereotype.Service;

import springbackend.service.MetricService;

/**
 * The Dahmerau-Lowenstein metric.
 */
@Service
public class MetricServiceImpl implements MetricService {

    public MetricServiceImpl() {
        this(DEFAULT_LENGTH);
    }

    public MetricServiceImpl(int maxLength) {
        currentRow = new int[maxLength + 1];
        previousRow = new int[maxLength + 1];
        transpositionRow = new int[maxLength + 1];
    }

    @Override
    public int getPrefixDistance(CharSequence string, CharSequence prefix, int max) {
        int prefixLength = prefix.length();
        if (max < 0) max = prefixLength;
        int stringLength = Math.min(string.length(), prefix.length() + max);

        if (prefixLength == 0)
            return 0;
        else if (stringLength == 0) return prefixLength;

        if (stringLength < prefixLength - max) return max + 1;

        if (prefixLength > currentRow.length) {
            currentRow = new int[prefixLength + 1];
            previousRow = new int[prefixLength + 1];
            transpositionRow = new int[prefixLength + 1];
        }

        for (int i = 0; i <= prefixLength; i++)
            previousRow[i] = i;

        int distance = Integer.MAX_VALUE;

        char lastStringCh = 0;
        for (int i = 1; i <= stringLength; i++) {
            char stringCh = string.charAt(i - 1);
            currentRow[0] = i;

            int from = Math.max(i - max - 1, 1);
            int to = Math.min(i + max + 1, prefixLength);

            char lastPrefixCh = 0;
            for (int j = from; j <= to; j++) {
                char prefixCh = prefix.charAt(j - 1);

                int cost = prefixCh == stringCh ? 0 : 1;
                int value = Math.min(Math.min(currentRow[j - 1] + 1, previousRow[j] + 1), previousRow[j - 1] + cost);

                if (prefixCh == lastStringCh && stringCh == lastPrefixCh)
                    value = Math.min(value, transpositionRow[j - 2] + cost);

                currentRow[j] = value;
                lastPrefixCh = prefixCh;
            }
            lastStringCh = stringCh;

            if (i >= prefixLength - max && i <= prefixLength + max && currentRow[prefixLength] < distance)
                distance = currentRow[prefixLength];

            int tempRow[] = transpositionRow;
            transpositionRow = previousRow;
            previousRow = currentRow;
            currentRow = tempRow;
        }

        return distance;
    }

    private static final int DEFAULT_LENGTH = 255;
    private int[] currentRow;
    private int[] previousRow;
    private int[] transpositionRow;
}