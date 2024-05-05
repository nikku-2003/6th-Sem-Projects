import tkinter as tk
from tkinter import messagebox
import random
import string
import time

def caesar_cipher(text, shift):
    result = ""
    for char in text:
        if char.isalpha():
            shifted = ord(char) + shift
            if char.islower():
                if shifted > ord('z'):
                    shifted -= 26
                elif shifted < ord('a'):
                    shifted += 26
            elif char.isupper():
                if shifted > ord('Z'):
                    shifted -= 26
                elif shifted < ord('A'):
                    shifted += 26
            result += chr(shifted)
        else:
            result += char
    return result

def generate_challenge():
    global plaintext, shift, encrypted_text, label, key_label
    plaintext = ''.join(random.choices(string.ascii_lowercase, k=random.randint(10, 20)))
    shift = random.randint(1, 25)
    encrypted_text = caesar_cipher(plaintext, shift)
    label.config(text=f"Encrypted Text: {encrypted_text}")
    key_label.config(text=f"Key: {shift}")

root = tk.Tk()
root.title("Caesar Cipher Breaker Challenge")
root.geometry("400x250")
label = tk.Label(root, text="")
label.pack()
key_label = tk.Label(root, text="")
key_label.pack()
generate_challenge()
entry = tk.Entry(root)
entry.pack()
start_time = time.time()

def check_answer():
    global start_time
    elapsed_time = time.time() - start_time
    if elapsed_time > 180:
        messagebox.showinfo("Time's up", "Sorry, you ran out of time.")
    else:
        user_answer = entry.get()
        if user_answer.lower() == plaintext.lower():
            messagebox.showinfo("Correct", "Congratulations You cracked the code.")
            generate_challenge()
        else:
            messagebox.showinfo("Incorrect", f"Sorry, the correct answer is: {plaintext}")
            entry.delete(0, tk.END)
submit_button = tk.Button(root, text="Submit", command=check_answer)
submit_button.pack()

def restart():
    global start_time
    entry.delete(0, tk.END)
    start_time = time.time()
    generate_challenge()

restart_button = tk.Button(root, text="Restart", command=restart)
restart_button.pack()

root.mainloop()