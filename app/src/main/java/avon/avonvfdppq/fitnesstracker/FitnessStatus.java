package avon.avonvfdppq.fitnesstracker;

/**
 * Created by Michael on 7/30/2018.
 */

public enum FitnessStatus {
    Success{
        public String toString(){
            return "Success";
        }
    },
    Fail{
        public String toString(){
            return "Fail";
        }
    },
    Pending{
        public String toString(){
            return "Pending";
        }
    },
    NotStarted{
        public String toString(){
            return "Not Started";
        }
    };
}
