##Following lines describe the endpoints present in the backend app
  ###GET
    1. /api/question:
        returns a list of all Question with their corresponding answers
        also removes the correct answer and replaces it with -1 :D
      Example:
        [
          {
            "id": 6,
            "question": "what is my name?",
            "subject": "non-technical",
            "options": [
              {
                "id": 13,
                "content": "server"
              },
              {
                "id": 14,
                "content": "cloud app"
              },
              {
                "id": 15,
                "content": "something"
              },
              {
                "id": 16,
                "content": "JSON"
              }
            ],
            "answer": -1
          }
        ]
    2. /api/question/{subject}:
        Returns a list of question for given category.
    3. /api/question/{id}
        Returns a single question for given id.
  ###POST
    1. /api/question
        Saves Question passed in the body to the database.
        Example of body:
            {
            	"id": 1,
            	"question" : "what is my name?",
            	"subject": "non-technical",
            	"options": ["foo", "cloud app", "something", "JSON"],
            	"answer" : 1
            }
    2. /api/check
        Upon receiving the list of answers checks them 
        and returns the total score of user
        Example: 
            {
            	"anws": [
            		{
            			"question_id": 6,
            			"answer_number": 1
            		}],
            		"ans_weight": 1
            }
          Where, "anws" is a array of objects 
          containing question's id and the user's answer
          "ans_weight" corresponds to how much one correct answer is worth