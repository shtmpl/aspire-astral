package aspire.astral;

final class Answers {

    static int answerTo(String question) {
        if (isAbout("life", question) && isAbout("universe", question) && isAbout("everything", question)) {
            return 42;
        }

        return -1;
    }

    private static boolean isAbout(String subject, String question) {
        if (subject == null || question == null) {
            return false;
        }

        return question.toLowerCase().contains(subject);
    }
}
