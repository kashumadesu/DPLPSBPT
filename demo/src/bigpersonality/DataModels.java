package bigpersonality;

import java.io.Serializable;
import java.util.Date;

// Personality trait enum
enum PersonalityTrait {
    OPENNESS,
    CONSCIENTIOUSNESS,
    EXTRAVERSION,
    AGREEABLENESS,
    NEUROTICISM
}

// Question class
class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text;
    private PersonalityTrait trait;
    
    public Question(String text, PersonalityTrait trait) {
        this.text = text;
        this.trait = trait;
    }
    
    public String getText() {
        return text;
    }
    
    public PersonalityTrait getTrait() {
        return trait;
    }
}

// Personality profile class
class PersonalityProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private int openness;
    private int conscientiousness;
    private int extraversion;
    private int agreeableness;
    private int neuroticism;
    private Date date;
    
    public PersonalityProfile(int openness, int conscientiousness, int extraversion, int agreeableness, int neuroticism) {
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
        this.date = new Date();
    }
    
    public int getOpenness() {
        return openness;
    }
    
    public int getConscientiousness() {
        return conscientiousness;
    }
    
    public int getExtraversion() {
        return extraversion;
    }
    
    public int getAgreeableness() {
        return agreeableness;
    }
    
    public int getNeuroticism() {
        return neuroticism;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}

// User data class
class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String middleName;
    private int age;
    private Date birthday;
    private String username;
    private String password;
    
    public UserData(String firstName, String lastName, String middleName, int age, Date birthday, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public int getAge() {
        return age;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}