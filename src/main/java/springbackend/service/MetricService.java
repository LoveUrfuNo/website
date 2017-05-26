/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service;

/**
 *
 */
public interface MetricService {
    /**
     * {@inheritDoc} Префиксное расстояние Дамерау-Левенштейна вычисляется за асимптотическое время O((max + 1) *
     * min(prefix.length(), string.length()))
     *
     * @param
     */
    int getPrefixDistance(CharSequence dictString, CharSequence userString, int max);
}
