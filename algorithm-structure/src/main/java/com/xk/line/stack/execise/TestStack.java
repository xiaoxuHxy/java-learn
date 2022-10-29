package com.xk.line.stack.execise;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TestStack {
    public static void main(String[] args) {
        String s = "{{}}";
        System.out.println(isValid(s));
    }

    /**
     * 有效括号问题
     * https://leetcode-cn.com/problems/valid-parentheses/
     */
    private static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('{', '}');
        map.put('[', ']');
        map.put('(', ')');
    }

    public static boolean isValid(String string) {
        int len = string.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            Character character = string.charAt(i);
            if (map.containsKey(character)) { //左括号放入栈中
                stack.push(character);
            } else {
                if (stack.isEmpty()) return false;
                Character left = stack.pop();
                if (character != map.get(left)) return false;
            }
        }
        return stack.isEmpty();
    }

}
