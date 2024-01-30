package ca.gbc.commententity;

import ca.gbc.commententity.dto.CommentRequest;
import ca.gbc.commententity.model.Comment;
import ca.gbc.commententity.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CommentEntityApplicationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CommentRepository repository;

    CommentRequest getCommentRequest() {
        return CommentRequest.builder()
                .userId("1")
                .postId("1")
                .comment("You are doing awesome!")
                .build();
    }

    private List<Comment> getCommentList() {
        List<Comment> commentList = new ArrayList<>();

        Comment comment = Comment.builder()
                .userId("1")
                .postId("1")
                .comment("You are doing awesome")
                .build();

        commentList.add(comment);
        return commentList;
    }

    @Test
    void createComments() throws Exception {
        CommentRequest commentRequest = getCommentRequest();
        String commentRequestString = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(commentRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertTrue(repository.findAll().size() > 0);
    }

    @Test
    void getComments() throws Exception {
        repository.saveAll(getCommentList());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comment")
                .accept(String.valueOf(MediaType.APPLICATION_JSON)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);

        int actualSize = jsonNodes.size();
        int expectedSize = getCommentList().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void updateComment() throws Exception {
        String id = "Comment";

        // Prepare saved comment
        Comment savedComment = Comment.builder()
                .id(id)
                .userId("1")
                .postId("1")
                .comment("This is a new comment")
                .build();

        repository.save(savedComment);

        savedComment.setComment("Testing integration");
        String commentRequestString = objectMapper.writeValueAsString(savedComment);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/comment/" + id)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(commentRequestString));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Comment query = Comment.builder().id(id).build();
        Optional<Comment> dbComment = repository.findOne(Example.of(query));
        Assertions.assertTrue(dbComment.isPresent());
        assertEquals("Testing integration", dbComment.get().getComment());
    }

    @Test
    void deleteComment() throws Exception {
        // Prepare saved comment
        String id = "comment";

        Comment savedComment = Comment.builder()
                .id(id)
                .userId("1")
                .postId("2")
                .comment("You are awesome!")
                .build();

        repository.save(savedComment);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/comment/" + id)
                .contentType(String.valueOf(MediaType.APPLICATION_JSON)));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());
    }


}
