#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <signal.h>
#include <ctype.h>
#include <arpa/inet.h>
#include <netdb.h>


#define LENGTH 1024

int main(int argc, char *argv[])
{
  printf("Você digitou %d argumentos.\n", argc);

  for (int i = 0; i < argc; i++) {
    printf("Argumento %d : %s\n", i, argv[i]);
  }

  int sockfd;
  struct sockaddr_in remote_addr;
  char buffer[LENGTH];

  char *ip = argv[1];

  // argument to int
  int port = atoi(argv[2]);

  char *filename = argv[3];
    
  sockfd = socket(AF_INET, SOCK_STREAM, 0);

  if (sockfd == -1)
  {
    printf("ERRO: Falhou em obter o Descritor do Socket.\n");
    return(0);
  }

  printf("\nVariaveis:\n");
  printf("IP: %s\n", ip);
  printf("Port: %d\n", port);
  printf("Filename: %s\n", filename);


  // fill the socket address struct
  remote_addr.sin_family = AF_INET;
  remote_addr.sin_port = htons(port);
  inet_pton(AF_INET, ip, &remote_addr.sin_addr);
  bzero(&(remote_addr.sin_zero), 8);


  if (connect(sockfd, (struct sockaddr *) &remote_addr, sizeof(struct sockaddr)) == -1)
  {
    printf("ERRO: Falhou em conectarse ao Host!\n");
    return(0);
  }
  else printf("Conectando-se...\n");

  FILE *fp = fopen(filename, "r");
  if(fp == NULL) printf("O Arquivo %s não foi encontrado ou não pode ser aberto.\n", filename);
  else
  {
    bzero(buffer, LENGTH);
    int f_block_sz;

    while((f_block_sz = fread(buffer, sizeof(char), LENGTH, fp)) > 0)
    {
      if(send(sockfd, buffer, f_block_sz, 0 ) < 0)
      {
        printf("ERRO: Falhou ao enviar o arquivo %s \n", filename);
      }
      bzero(buffer, LENGTH);
    }
    printf("Arquivo enviado.\n");
    close(sockfd);
    printf("Conexão finalizada.\n");
  }
}
