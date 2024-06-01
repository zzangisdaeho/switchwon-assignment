package com.switchwon.user.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User {

    private String userId;

    @Builder.Default
    private List<Point> points = new ArrayList<>();

    public List<Point> points(){
        return this.points;
    }

    public List<? extends Point> getPoints(){
        return points;
    }

    public void addBalance(Point point){
        this.points.add(point);
        point.setUser(this);
    }
}
