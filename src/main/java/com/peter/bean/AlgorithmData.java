package com.peter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lcc
 * @date 2020/10/14 下午2:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AlgorithmData {
    private double length;
    private int points;
    private double time;
    private long timestamp;
}
