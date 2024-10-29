package com.rupesh.assignment.lostfound.utils;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.rupesh.assignment.lostfound.domain.LostItem;

/**
 * This class is to generate the custom ID for the lost item. EG: If the lost Item name is "Apple
 * Watch", It will generate ID something like "APP213". This is so that the product name is easily
 * understandable from it's ID. It will also removed the excess spaces or tabs, EG: "HP Laptop" will
 * be "HPL232"
 * 
 * @author Rupesh
 *
 */
public class LostItemIDGenerator implements IdentifierGenerator {

  private static final long serialVersionUID = 1L;
  private static final Random RANDOM = new Random();

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object obj) {
    LostItem item = (LostItem) obj;
    return (item.getName().replaceAll("\\s+", "").substring(0, 3)).toUpperCase()
        + String.format("%03d", 100 + RANDOM.nextInt(900));
  }
}
