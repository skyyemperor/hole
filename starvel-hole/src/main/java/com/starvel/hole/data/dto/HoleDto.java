package com.starvel.hole.data.dto;

import com.starvel.hole.data.po.Hole;
import com.starvel.hole.data.po.HoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by skyyemperor on 2021-01-05 0:28
 * Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoleDto {
    private Hole hole;
    private HoleUser holeUser;
}
