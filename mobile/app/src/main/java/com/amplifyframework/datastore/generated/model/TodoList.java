// Generated using amplify-cli-version: 7.6.5, amplify-codegen-version: 2.26.21

package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;
import java.util.List;

/** This is an auto generated class representing the TodoList type in your schema. */
public final class TodoList {
  private final String name;
  private final String parentName;
  public String getName() {
      return name;
  }
  
  public String getParentName() {
      return parentName;
  }
  
  private TodoList(String name, String parentName) {
    this.name = name;
    this.parentName = parentName;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TodoList todoList = (TodoList) obj;
      return ObjectsCompat.equals(getName(), todoList.getName()) &&
              ObjectsCompat.equals(getParentName(), todoList.getParentName());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getName())
      .append(getParentName())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(name,
      parentName);
  }
  public interface BuildStep {
    TodoList build();
    BuildStep name(String name);
    BuildStep parentName(String parentName);
  }
  

  public static class Builder implements BuildStep {
    private String name;
    private String parentName;
    @Override
     public TodoList build() {
        
        return new TodoList(
          name,
          parentName);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String name, String parentName) {
      super.name(name)
        .parentName(parentName);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder parentName(String parentName) {
      return (CopyOfBuilder) super.parentName(parentName);
    }
  }
  
}
