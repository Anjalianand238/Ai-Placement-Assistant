package com.placement.assistant.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class GeminiAiService {
    @Value("${gemini.api.key}") private String apiKey;
    private static final String GEMINI_URL="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private final RestTemplate restTemplate=new RestTemplate();
    private final ObjectMapper objectMapper=new ObjectMapper();

    public boolean isApiKeyConfigured(){return apiKey!=null&&!apiKey.equals("YOUR_GEMINI_API_KEY_HERE")&&!apiKey.isBlank();}

    private String callGemini(String prompt){
        Map<String,Object> body=new HashMap<>();
        body.put("contents",List.of(Map.of("role","user","parts",List.of(Map.of("text",prompt)))));
        body.put("generationConfig",Map.of("responseMimeType","application/json"));
        HttpHeaders h=new HttpHeaders(); h.setContentType(MediaType.APPLICATION_JSON);
        try{
            String resp=restTemplate.postForObject(GEMINI_URL+"?key="+apiKey,new HttpEntity<>(body,h),String.class);
            JsonNode root=objectMapper.readTree(resp);
            return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        }catch(Exception e){e.printStackTrace(); return null;}
    }

    @SuppressWarnings("unchecked")
    private Map<String,Object> parse(String json){
        if(json==null) return new HashMap<>();
        try{return objectMapper.readValue(json,Map.class);}catch(Exception e){return new HashMap<>();}
    }

    public Map<String,Object> analyzeSkillGap(List<String> skills,String jd,String jobTitle){
        if(!isApiKeyConfigured()) return Map.of(
            "missingSkills",List.of(Map.of("skill","DSA & Algorithms","priority","critical","reason","Essential for tech interviews"),Map.of("skill","Spring Security","priority","critical","reason","Required for secure backend"),Map.of("skill","Docker","priority","recommended","reason","Modern deployment knowledge"),Map.of("skill","AWS","priority","optional","reason","Cloud knowledge is a plus")),
            "presentSkills",skills.isEmpty()?List.of("Java","Spring Boot","MySQL"):skills,
            "matchScore",65,"summary","Good Java foundation! Focus on DSA and Spring Security to become fully job-ready. [Demo mode - add Gemini API key for real analysis]");
        return parse(callGemini("Analyze skill gap. Student skills: "+skills+". Job Title: "+jobTitle+". JD: "+jd+". Return JSON: {missingSkills:[{skill,priority,reason}],presentSkills:[],matchScore:0-100,summary}"));
    }

    public Map<String,Object> parseJobDescription(String jd){
        if(!isApiKeyConfigured()) return Map.of("requiredSkills",List.of("Java","Spring Boot","MySQL","REST API","Git","Docker"),"experienceRequired","0-2 years","jobType","Backend Developer","note","Demo mode - add Gemini API key for real extraction");
        return parse(callGemini("Extract from this JD: "+jd+". Return JSON: {requiredSkills:[],experienceRequired,jobType}"));
    }

    public Map<String,Object> analyzeResume(String resumeText){
        if(!isApiKeyConfigured()) return Map.of("extractedSkills",List.of("Java","Spring Boot","MySQL","HTML","CSS","JavaScript"),"summary","Strong Java backend profile with web fundamentals.","suggestions",List.of("Learn Data Structures & Algorithms","Add Docker and containerization","Contribute to open source projects","Get AWS Cloud Practitioner certification"),"note","Demo mode - add Gemini API key for real resume analysis");
        return parse(callGemini("Analyze this resume: "+resumeText+". Return JSON: {extractedSkills:[],summary,suggestions:[]}"));
    }

    public Map<String,Object> generateRoadmap(List<String> missingSkills,String targetRole){
        if(!isApiKeyConfigured()) return Map.of("title","Java Developer Roadmap","roadmap",List.of(Map.of("week",1,"topic","Data Structures & Algorithms","resources",List.of("LeetCode Easy problems","GeeksForGeeks DSA"),"description","Arrays, Strings, Linked Lists"),Map.of("week",2,"topic","Spring Security & JWT","resources",List.of("Official Spring Security Docs","Baeldung tutorials"),"description","Authentication and Authorization"),Map.of("week",3,"topic","Docker Basics","resources",List.of("Docker official tutorial","TechWorld with Nana"),"description","Containers and Dockerfiles"),Map.of("week",4,"topic","AWS Fundamentals","resources",List.of("AWS Free Tier","AWS Cloud Practitioner course"),"description","EC2, S3, RDS basics")),"note","Demo mode - add Gemini API key for personalized roadmap");
        return parse(callGemini("Create learning roadmap for "+targetRole+" focusing on: "+missingSkills+". Return JSON: {title,roadmap:[{week,topic,resources:[],description}]}"));
    }

    public Map<String,Object> generateInterviewQuestions(String jobTitle,List<String> skills){
        if(!isApiKeyConfigured()) return Map.of("questions",List.of(Map.of("question","What is Dependency Injection in Spring Boot?","hint","Explain IoC container and @Autowired annotation"),Map.of("question","Explain the difference between @RestController and @Controller","hint","@RestController = @Controller + @ResponseBody"),Map.of("question","What is JPA and how does Hibernate relate to it?","hint","JPA is specification, Hibernate is implementation"),Map.of("question","How does JWT authentication work?","hint","Header.Payload.Signature - stateless auth"),Map.of("question","What are the main HTTP methods and when do you use each?","hint","GET, POST, PUT, DELETE, PATCH - RESTful principles")),"note","Demo mode - add Gemini API key for personalized questions");
        return parse(callGemini("Generate 5 interview Q&A for "+jobTitle+" with skills: "+skills+". Return JSON: {questions:[{question,hint}]}"));
    }

    public Map<String,Object> calculateJobMatch(List<String> skills,String jd,String jobTitle){
        if(!isApiKeyConfigured()) return Map.of("matchScore",68,"matchedSkills",skills.isEmpty()?List.of("Java","MySQL"):skills,"missingSkills",List.of("Docker","AWS","Spring Security"),"recommendation","You have a solid foundation! Learn Docker and Spring Security to significantly boost your match score.","note","Demo mode - add Gemini API key for real matching");
        return parse(callGemini("Calculate job match. Student skills: "+skills+". Job: "+jobTitle+". JD: "+jd+". Return JSON: {matchScore:0-100,matchedSkills:[],missingSkills:[],recommendation}"));
    }
}
