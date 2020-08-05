/* 
User: Urmi
Date: 7/4/2020 
Time: 4:07 PM
*/

package com.in28minutes.microservices.limitsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LimitsConfiguration {

    private int maximum;
    private int minimum;
}
