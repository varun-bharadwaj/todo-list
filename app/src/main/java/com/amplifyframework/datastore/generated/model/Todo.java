// Generated using amplify-cli-version: 7.6.5, amplify-codegen-version: 2.26.21

package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Todo type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Todos", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Todo implements Model {
  public static final QueryField ID = field("Todo", "id");
  public static final QueryField NAME = field("Todo", "name");
  public static final QueryField COMPLETED = field("Todo", "completed");
  public static final QueryField DUE = field("Todo", "due");
  public static final QueryField TAGS = field("Todo", "tags");
  public static final QueryField PARENT_LIST = field("Todo", "parentList");
  public static final QueryField USER = field("Todo", "user");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String completed;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime due;
  private final @ModelField(targetType="String") List<String> tags;
  private final @ModelField(targetType="TodoList") TodoList parentList;
  private final @ModelField(targetType="String") String user;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getCompleted() {
      return completed;
  }
  
  public Temporal.DateTime getDue() {
      return due;
  }
  
  public List<String> getTags() {
      return tags;
  }
  
  public TodoList getParentList() {
      return parentList;
  }
  
  public String getUser() {
      return user;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Todo(String id, String name, String completed, Temporal.DateTime due, List<String> tags, TodoList parentList, String user) {
    this.id = id;
    this.name = name;
    this.completed = completed;
    this.due = due;
    this.tags = tags;
    this.parentList = parentList;
    this.user = user;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Todo todo = (Todo) obj;
      return ObjectsCompat.equals(getId(), todo.getId()) &&
              ObjectsCompat.equals(getName(), todo.getName()) &&
              ObjectsCompat.equals(getCompleted(), todo.getCompleted()) &&
              ObjectsCompat.equals(getDue(), todo.getDue()) &&
              ObjectsCompat.equals(getTags(), todo.getTags()) &&
              ObjectsCompat.equals(getParentList(), todo.getParentList()) &&
              ObjectsCompat.equals(getUser(), todo.getUser()) &&
              ObjectsCompat.equals(getCreatedAt(), todo.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), todo.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getCompleted())
      .append(getDue())
      .append(getTags())
      .append(getParentList())
      .append(getUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Todo {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("completed=" + String.valueOf(getCompleted()) + ", ")
      .append("due=" + String.valueOf(getDue()) + ", ")
      .append("tags=" + String.valueOf(getTags()) + ", ")
      .append("parentList=" + String.valueOf(getParentList()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Todo justId(String id) {
    return new Todo(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      completed,
      due,
      tags,
      parentList,
      user);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Todo build();
    BuildStep id(String id);
    BuildStep completed(String completed);
    BuildStep due(Temporal.DateTime due);
    BuildStep tags(List<String> tags);
    BuildStep parentList(TodoList parentList);
    BuildStep user(String user);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String completed;
    private Temporal.DateTime due;
    private List<String> tags;
    private TodoList parentList;
    private String user;
    @Override
     public Todo build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Todo(
          id,
          name,
          completed,
          due,
          tags,
          parentList,
          user);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep completed(String completed) {
        this.completed = completed;
        return this;
    }
    
    @Override
     public BuildStep due(Temporal.DateTime due) {
        this.due = due;
        return this;
    }
    
    @Override
     public BuildStep tags(List<String> tags) {
        this.tags = tags;
        return this;
    }
    
    @Override
     public BuildStep parentList(TodoList parentList) {
        this.parentList = parentList;
        return this;
    }
    
    @Override
     public BuildStep user(String user) {
        this.user = user;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String completed, Temporal.DateTime due, List<String> tags, TodoList parentList, String user) {
      super.id(id);
      super.name(name)
        .completed(completed)
        .due(due)
        .tags(tags)
        .parentList(parentList)
        .user(user);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder completed(String completed) {
      return (CopyOfBuilder) super.completed(completed);
    }
    
    @Override
     public CopyOfBuilder due(Temporal.DateTime due) {
      return (CopyOfBuilder) super.due(due);
    }
    
    @Override
     public CopyOfBuilder tags(List<String> tags) {
      return (CopyOfBuilder) super.tags(tags);
    }
    
    @Override
     public CopyOfBuilder parentList(TodoList parentList) {
      return (CopyOfBuilder) super.parentList(parentList);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  
}
